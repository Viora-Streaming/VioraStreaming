import {API_PAGE} from "../constants/routingConstants.ts";
import {Navigate, Outlet} from "react-router-dom";

export const AnonRoute = () => {
  const token = localStorage.getItem('JWT_TOKEN');

  if (token) {
    return <Navigate to={API_PAGE.Home} replace />;
  }

  return <Outlet/>;
};