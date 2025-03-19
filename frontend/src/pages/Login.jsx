import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';
import api from '../api/axios';

function Login() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    userName: '',
    passWord: '',
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await api.post('/public/login', formData);
      // alert(JSON.stringify(response))
      // console.log(response)
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('role',response.data.role[0]);
      toast.success('Login successful!');   
      navigate('/');
    } catch (error) {
      console.log(error)
      toast.error(error.response?.data?.message || 'Login failed');
    }
  };

  return (
    <div className="max-w-md mx-auto bg-white p-8 rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-6 text-center">Login</h2>
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
        <button type="submit" className="w-full btn-primary">
          Login
        </button>
      </form>
    </div>
  );
}

export default Login;