const express = require('express');
const multer = require('multer');
const path = require('path');

const app = express();
const port = 3000;

// Multer configuration for handling file uploads
const storage = multer.diskStorage({
  destination: path.join(__dirname, '/save'), // Set the destination directory for storing uploaded files
  filename: function (req, file, cb) {
    cb(null, file.originalname);
  }
});

const upload = multer({ storage: storage });

// Handling the file upload
app.post('/api/upload', upload.single('file'), (req, res) => {
  if (!req.file) {
    return res.status(400).json({ error: 'No file provided' });
  }

  const filename = req.file.originalname;
  // Perform additional processing if needed

  res.json({ filename }); // Respond with information about the uploaded file
});

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
