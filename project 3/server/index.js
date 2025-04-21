// server/index.js
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());

mongoose.connect('mongodb://localhost:27017/dailyjournal', {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

const EntrySchema = new mongoose.Schema({
  title: String,
  content: String,
  createdAt: { type: Date, default: Date.now },
});

const Entry = mongoose.model('Entry', EntrySchema);

app.get('/entries', async (req, res) => {
  const entries = await Entry.find().sort({ createdAt: -1 });
  res.json(entries);
});

app.post('/entries', async (req, res) => {
  const newEntry = new Entry(req.body);
  await newEntry.save();
  res.json(newEntry);
});

app.put('/entries/:id', async (req, res) => {
  const updated = await Entry.findByIdAndUpdate(req.params.id, req.body, { new: true });
  res.json(updated);
});

app.delete('/entries/:id', async (req, res) => {
  await Entry.findByIdAndDelete(req.params.id);
  res.json({ message: 'Deleted' });
});

app.listen(5000, () => console.log('Server running at http://localhost:5000'));