import {Component, OnInit, ViewChild} from '@angular/core';
import { NgChartsModule } from 'ng2-charts';
import {Chart, ChartDataset, ChartType} from "chart.js";

import {CustomerService} from "../services/customer.service";
import {forkJoin, map, Observable} from "rxjs";


import {
  ApexAxisChartSeries,
  ApexChart, ApexFill, ApexLegend, ApexNonAxisChartSeries, ApexPlotOptions, ApexResponsive,
  ApexTitleSubtitle, ApexTooltip,
  ApexXAxis, ApexYAxis,
  ChartComponent,
  NgApexchartsModule
} from "ng-apexcharts";
import {AccountService} from "../services/account.service";
import {AccountDetails, AccountsDetails} from "../model/account.model";
import _default from "chart.js/dist/plugins/plugin.tooltip";
import numbers = _default.defaults.animations.numbers;
import {CustomersComponent} from "../customers/customers.component";
import {CommonModule, NgIf} from "@angular/common";
import {Agent} from "../model/agent.model";
import {AgentComponent} from "../agent/agent.component";
export class Utils {
  static months(options: { count: number }): string[] {
    // Your implementation of the months method here
    // For example, return an array of month names based on the count
    const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
    return months.slice(0, options.count);
  }
}



@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    NgChartsModule,
    NgApexchartsModule,
    CustomersComponent,
    CommonModule,
    NgIf,
    AgentComponent


  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})



export class DashboardComponent implements OnInit {

  customers$!: Observable<any[]>;
  custCount: number = 0;
  agCount: number = 0;
  list = Array.from({ length: 12 }, () => 0);

  listC = Array.from({ length: 12 }, () => 0);

  listS = Array.from({ length: 12 }, () => 0);

  chartType!: string;
   data!:string;

  series1!: ApexAxisChartSeries;
  chart1!: ApexChart;
  xaxis1!: ApexXAxis;
  yaxis1!: ApexYAxis | ApexYAxis[];
  title1!: ApexTitleSubtitle;
  labels1!: string[];
  stroke1!: any; // ApexStroke;
  dataLabels1!: any; // ApexDataLabels;
  fill1!: ApexFill;
  tooltip1!: ApexTooltip;


  series!: ApexAxisChartSeries;
  chart!: ApexChart;
  xaxis!: ApexXAxis;
  title!: ApexTitleSubtitle;


  series2!: ApexNonAxisChartSeries;
  chart2!: ApexChart;
  labels2!: string[];
  colors2!: string[];
  legend2!: ApexLegend;
  plotOptions2!: ApexPlotOptions;
  responsive2!:ApexResponsive[];
  private accounts$!: Observable<Array<AccountsDetails>>;
  private savingAccountsCount!: number;
  private currentAccountsCount!: number;
  private customersThisMonth!: number;
  private money!: number;
  private s!:  ApexNonAxisChartSeries;
  private op!:number;
  private agent$!: Observable<Array<Agent>>;
  private accountsList!: Array<AccountsDetails>;




  constructor(private customerService: CustomerService,private accountService: AccountService) {
  this.initializeChart();

  }

  initializeChart(): void {
    this.series = [{
      name: 'series-1',
      data: [44, 55, 13, 33,0,0]
    }];

    this.chart = {
      height: 350,
      type: 'bar'
    };

    this.xaxis = {
      categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep']
    };

    this.title = {
      text: ''
    };

    this.series1 = [
      {
        name: 'Saving Accounts',
        type: 'column',
        data: this.listS
      },
      {
        name: 'Current Accounts',
        type: 'line',
        data: this.listC
      }
    ];

    this.chart1 = {
      height: 300,
      type: 'line'
    };

    this.fill1 = {
      opacity: [0.85, 0.25, 1]
    };

    this.title1 = {
      text: ''
    };

    this.tooltip1 = {
      shared: true,
      intersect: false
    };

    this.xaxis1 = {
      type: 'datetime',
      categories: [
        '01 Jan 2001',
        '01 Feb 2001',
        '01 Mar 2001',
        '01 Apr 2001',
        '01 May 2001',
        '01 Jun 2001',
        '01 Jul 2001',
        '01 Aug 2001',
        '01 Sep 2001',

      ]
    };

    this.yaxis1 = [
      {
        title: {
          text: 'Saving Accounts'
        }
      },
      {
        opposite: true,
        title: {
          text: 'Current Accounts'
        }
      }
    ];


    //monthly target

    this.series2 = [this.customersThisMonth, this.currentAccountsCount, this.savingAccountsCount];
    this.s = [this.money]

    this.chart2 = {
      height: 300,
      type: 'radialBar'
    };

    this.plotOptions2 = {
      radialBar: {
        dataLabels: {
          name: {
            fontSize: '22px'
          },
          value: {
            fontSize: '16px'
          },
          total: {
            show: true,
            label: 'Targets',
            formatter: function (w) {

              return ``;
            }
          }
        }
      }
    };

    this.labels2 = ['Customers', 'Saving Accounts', 'Currents Accounts'];

  };


  ngOnInit(): void {
//customers

    this.customers$ = this.customerService.getCustomers();
    this.agent$ = this.customerService.getAgent();
    this.agent$.subscribe(agent => {
      this.agCount = agent.length;



      console.log("agent: "+agent);
      agent.forEach((ag, index) => {


        console.log(`agent ${index + 1}:`, ag);
        // this month

      });
    });


    this.customers$.subscribe(customers => {
      const monthCounts = new Map<number, number>();
      this.custCount = customers.length;
      console.log('Total number of customers:', customers);

      customers.forEach((customer, index) => {
        const month = new Date(customer.timestamp).getMonth(); // Adding 1 to get 1-based index
        // Increment the count for the month
        if (monthCounts.has(month)) {
          monthCounts.set(month, monthCounts.get(month)!+1);
        } else {
          monthCounts.set(month, 1);
        }

        console.log(`Element ${index + 1}:`, customer);
        // this month

        const currentMonth = new Date().getMonth() + 1;
        const customersThisMonth =customers.filter(
          customer => new Date(customer.timestamp).getMonth() + 1 === currentMonth
        );

        this.customersThisMonth = customersThisMonth.length;


      });



      // Log the count for each month
      monthCounts.forEach((count, month) => {
        console.log(`Customers created in month ${month}: ${count}`);
        this.list[month] = count;
      });




      this.RenderChart();

    });

//acounts
  this.accounts$ = this.accountService.getAccounts();
    console.log("here"+this.accounts$)
    let money=0;


  console.log("her"+this.accounts$);
    this.accounts$.subscribe(acc => {
      this.savingAccountsCount = acc.filter(account => account.type === 'SavingAccount').length;
      this.currentAccountsCount = acc.filter(account => account.type === 'CurrentAccount').length;


      console.log("operation1");
      //
      // acc.forEach(account => {
      //
      //   this.accountService.getOp(account.id).subscribe((accountData) => {
      //
      //     this.op += accountData.toString().length;
      //     console.log(this.op);
      //   });
      //
      // });

      // const observables = acc.map(account => this.accountService.getOp(account.id));
      //
      // forkJoin(observables).subscribe(accountDataArray => {
      //   console.log("accountDataArray:", accountDataArray);
      //
      //    this.accountDataArray$.reduce((sum, accountData) => sum + accountData.toString().length, 0);
      //   console.log("oph:", this.op);
      // });

      console.log("oper"+this.op);


      const monthCounts = new Map<number, number>();


      const s = acc.filter(account => account.type === 'SavingAccount')
      s.forEach((accounts, index) => {
        const month = new Date(accounts.createdAt).getMonth(); // Adding 1 to get 1-based index
        // Increment the count for the month
        if (monthCounts.has(month)) {
          monthCounts.set(month, monthCounts.get(month)!+1);
        } else {
          monthCounts.set(month, 1);
        }
        console.log(`Element ${index + 1}:`, accounts.balance);
        money += accounts.balance;
      });

      // Log the count for each month
      monthCounts.forEach((count, month) => {
        console.log(`Customers created in month ${month}: ${count}`);
        this.listS[month] = count;
      });

      const monthCount = new Map<number, number>();

      const d = acc.filter(account => account.type === 'CurrentAccount')
      console.log("Dd"+d);
      d.forEach((accounts, index) => {
        const month = new Date(accounts.createdAt).getMonth(); // Adding 1 to get 1-based index
        // Increment the count for the month
        if (monthCount.has(month)) {
          monthCount.set(month, monthCount.get(month)!+1);
        } else {
          monthCount.set(month, 1);
        }
        console.log(`Element ${index + 1}:`, accounts.balance);
        money += accounts.balance;
      });

      // Log the count for each month
      monthCount.forEach((count, month) => {
        console.log(`Customers created in month ${month}: ${count}`);
        this.listC[month] = count;
      });

      console.log(money+"dollar");

      this.money = money;


      this.initializeChart();





    });




  }




  RenderChart(){
    console.log('this.list:', this.list);


    const labels = Utils.months({count: 12});
    const data =   new Chart("data", {
      type: 'bar',
      data: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'],
        datasets: [{
          label: 'number of client',
          data: this.list,
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }






}
