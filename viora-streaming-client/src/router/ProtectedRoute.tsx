import {Navigate} from 'react-router-dom';
import {API_PAGE} from "../constants/routingConstants.ts";
import {AppLayout} from "../components/Layout/AppLayout.tsx";

const ProtectedRoute = () => {
  const token = localStorage.getItem('JWT_TOKEN');

  // if (!token) {
  //   return <Navigate to={API_PAGE.Auth} replace/>
  // }

  return (
      <AppLayout/>
  )
};

export default ProtectedRoute;