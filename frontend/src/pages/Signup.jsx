import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';
import api from '../api/axios';

function Signup() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    userName: '',
    passWord: '',
    email: '',
    sentimentAnalysis: false,
  });

  const handleChange = (e) => {
    const value = e.target.type === 'checkbox' ? e.target.checked : e.target.value;
    setFormData({ ...formData, [e.target.name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post('/public/signup', formData);
      toast.success('Signup successful! Please login.');
      navigate('/login');
    } catch (error) {
      toast.error(error.response?.data?.message || 'Signup failed');
    }
  };

  return (
    <div className="max-w-md mx-auto bg-white p-8 rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-6 text-center">Sign Up</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">Username</label>
          <input
            type="text"
            name="userName"
            value={formData.userName}
            onChange={handleChange}
            className="input-field"
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Password</label>
          <input
            type="password"
            name="passWord"
            value={formData.passWord}
            onChange={handleChange}
            className="input-field"
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Email</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            className="input-field"
            required
          />
        </div>
        <div className="flex items-center">
          <input
            type="checkbox"
            name="sentimentAnalysis"
            checked={formData.sentimentAnalysis}
            onChange={handleChange}
            className="h-4 w-4 text-blue-600"
          />
          <label className="ml-2 text-sm text-gray-700">Enable Sentiment Analysis</label>
        </div>
        <button type="submit" className="w-full btn-primary">
          Sign Up
        </button>
      </form>
    </div>
  );
}

export default Signup;