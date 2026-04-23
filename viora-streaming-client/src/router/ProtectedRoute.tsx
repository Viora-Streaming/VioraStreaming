import {Navigate, Outlet} from 'react-router-dom';
import {API_PAGE} from "../constants/routingConstants.ts";

const ProtectedRoute = () => {
  const token = localStorage.getItem('JWT_TOKEN');

  if (!token) {
    return <Navigate to={API_PAGE.Auth} replace />;
  }

  return <Outlet />;
};

export default ProtectedRoute;