// client/src/App.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './App.css'

export default function App() {
  const [entries, setEntries] = useState([]);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [search, setSearch] = useState('');

  const fetchEntries = async () => {
    const res = await axios.get('http://localhost:5000/entries');
    setEntries(res.data);
  };

  useEffect(() => {
    fetchEntries();
  }, []);

  const addEntry = async () => {
    if (!title || !content) return;
    await axios.post('http://localhost:5000/entries', { title, content });
    setTitle('');
    setContent('');
    fetchEntries();
  };

  const deleteEntry = async (id) => {
    await axios.delete(`http://localhost:5000/entries/${id}`);
    fetchEntries();
  };

  const filteredEntries = entries.filter((e) =>
    e.title.toLowerCase().includes(search.toLowerCase()) ||
    e.content.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="p-6 max-w-xl mx-auto font-sans">
      <h1 className="text-2xl font-bold mb-4">📓 Daily Journal</h1>

      <input
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        placeholder="🔍 Search..."
        className="border p-2 w-full mb-4"
      />

      <input
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        placeholder="Title"
        className="border p-2 w-full mb-2"
      />
      <textarea
        value={content}
        onChange={(e) => setContent(e.target.value)}
        placeholder="Write something..."
        className="border p-2 w-full mb-2"
      />
      <button onClick={addEntry} className="bg-blue-500 text-white px-4 py-2 rounded">
        ➕ Add Entry
      </button>

      <div className="mt-6">
        {filteredEntries.map((entry) => (
          <div key={entry._id} className="border p-4 mb-2 rounded shadow">
            <h2 className="font-semibold text-lg">{entry.title}</h2>
            <p>{entry.content}</p>
            <button
              onClick={() => deleteEntry(entry._id)}
              className="text-red-500 mt-2"
            >
              🗑️ Delete
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
