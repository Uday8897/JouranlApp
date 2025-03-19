import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Toaster } from 'react-hot-toast';
import Navbar from './components/Navbar';
import Login from './pages/Login';
import Signup from './pages/Signup';
import Journal from './pages/Journal';
import PrivateRoute from './components/PrivateRoute';
import Swagger from './pages/Swagger';
import FEWD from './pages/FEWD';
function App() {
  return (
    <>
    <FEWD/>
    </>
    // <Router>
    //   <div className="min-h-screen bg-gray-50">
    //     <Navbar />
    //     <div className="container mx-auto px-4 py-8">
    //       <Routes>
    //         <Route path="/login" element={<Login />} />
    //         <Route path="/signup" element={<Signup />} />
    //         <Route path="/" element={
    //           <PrivateRoute>
    //           <Journal />
    //         </PrivateRoute>
    //         } />
    //         <Route path='/docs' element={
    //           <PrivateRoute>
    //           <Swagger/>
    //         </PrivateRoute>
    //         }>
               
    //         </Route>
    //       </Routes>
    //     </div>
    //     <Toaster position="top-center" />
    //   </div>
    // </Router>
  );
}

export default App;