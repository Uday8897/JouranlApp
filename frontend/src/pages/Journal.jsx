import { useState, useEffect } from 'react';
import toast from 'react-hot-toast';
import api from '../api/axios';

function Journal() {
  const [entries, setEntries] = useState([]);
  const [newEntry, setNewEntry] = useState({ title: '', content: '' });
  const [editingEntry, setEditingEntry] = useState(null);
  const [loadingReport, setLoadingReport] = useState(false); // New state
  const [reportAvailable, setReportAvailable] = useState(false);
  const [reportUrl, setReportUrl] = useState('');

  useEffect(() => {
    fetchEntries();
  }, []);

  const fetchEntries = async () => {
    try {
      const response = await api.get('/journal');
      setEntries(response.data);
    } catch (error) {
      toast.error('Failed to fetch entries');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingEntry) {
        await api.put(`/journal/id/${editingEntry.id}`, {
          ...editingEntry,
          ...newEntry,
        });
        toast.success('Entry updated successfully');
      } else {
        await api.post('/journal', newEntry);
        toast.success('Entry created successfully');
      }
      setNewEntry({ title: '', content: '' });
      setEditingEntry(null);
      fetchEntries();
    } catch (error) {
      toast.error(error.response?.data?.message || 'Operation failed');
    }
  };

  const handleDelete = async (id) => {
    try {
      await api.delete(`/journal/id/${id}`);
      toast.success('Entry deleted successfully');
      fetchEntries();
    } catch (error) {
      toast.error('Failed to delete entry');
    }
  };

  const handleEdit = (entry) => {
    setEditingEntry(entry);
    setNewEntry({
      title: entry.title,
      content: entry.content,
    });
  };

  const requestReport = async () => {
    setLoadingReport(true); // Start loading
    try {
      const response = await api.get('/user/send-my-report', {
        responseType: 'blob', // Ensures the response is treated as a file
      });

      const blob = new Blob([response.data], { type: 'text/plain' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'Weekly_Report.txt';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);

      toast.success('Report downloaded successfully!');
    } catch (error) {
      toast.error('Failed to generate report');
    } finally {
      setLoadingReport(false); // Stop loading
    }
  };

  return (
    <div className="max-w-4xl mx-auto">
      <div className="bg-white p-6 rounded-lg shadow-md mb-8">
        <h2 className="text-2xl font-bold mb-4">
          {editingEntry ? 'Edit Entry' : 'New Entry'}
        </h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <input
              type="text"
              placeholder="Title"
              value={newEntry.title}
              onChange={(e) => setNewEntry({ ...newEntry, title: e.target.value })}
              className="input-field"
              required
            />
          </div>
          <div>
            <textarea
              placeholder="Content"
              value={newEntry.content}
              onChange={(e) => setNewEntry({ ...newEntry, content: e.target.value })}
              className="input-field min-h-[150px]"
              required
            />
          </div>
          <div className="flex gap-2">
            <button type="submit" className="btn-primary">
              {editingEntry ? 'Update Entry' : 'Create Entry'}
            </button>
            {editingEntry && (
              <button
                type="button"
                onClick={() => {
                  setEditingEntry(null);
                  setNewEntry({ title: '', content: '' });
                }}
                className="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600"
              >
                Cancel
              </button>
            )}
          </div>
        </form>
      </div>

      <div className="space-y-4">
        {entries.map((entry) => (
          <div key={entry.id} className="bg-white p-6 rounded-lg shadow-md">
            <h3 className="text-xl font-semibold mb-2">{entry.title}</h3>
            <p className="text-gray-600 mb-4">{entry.content}</p>
            <div className="flex gap-2">
              <button
                onClick={() => handleEdit(entry)}
                className="px-3 py-1 bg-blue-500 text-white rounded hover:bg-blue-600"
              >
                Edit
              </button>
              <button
                onClick={() => handleDelete(entry.id)}
                className="px-3 py-1 bg-red-500 text-white rounded hover:bg-red-600"
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>

      {/* Get My Weekly Report Button */}
      <div className="mt-6 flex flex-col items-center">
        <button
          onClick={requestReport}
          disabled={loadingReport}
          className={`px-4 py-2 text-white rounded-lg ${
            loadingReport ? 'bg-gray-400 cursor-not-allowed' : 'bg-green-500 hover:bg-green-600'
          }`}
        >
          {loadingReport ? 'Generating Report...' : 'Get My Weekly Report'}
        </button>
      </div>
    </div>
  );
}

export default Journal;
