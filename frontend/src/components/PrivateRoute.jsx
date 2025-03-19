import { Navigate } from 'react-router-dom';

function PrivateRoute({ children }) {
  const token = localStorage.getItem('token');
  const role =localStorage.getItem('role')
  
  if (!token ) {
    return <Navigate to="/login" />;
  }
  if(token && role==='ADMIN'){
    return <Navigate to="/admin"/>
  }

  return children;
}

export default PrivateRoute;