import { Link, useNavigate } from 'react-router-dom';

function Navbar() {
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem("role");
    navigate('/login');
  };

  return (
    <nav className="bg-white shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center h-16">
          <Link to="/" className="text-xl font-bold text-blue-600">
            Journal App
          </Link>
          <div className="flex items-center gap-4">
            {token ? (
              <>
                  <button
                onClick={handleLogout}
                className="btn-primary"
              >
                Logout
              </button>
              <button   className="btn-primary"
 onClick={()=>navigate("/docs")}>
                Swagger Api Docs
              </button>
              </>
          
              
            ) : (
              <>
                <Link to="/login" className="btn-primary">
                  Login
                </Link>
                <Link to="/signup" className="btn-primary">
                  Sign Up
                </Link>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;