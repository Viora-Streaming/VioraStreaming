import type {RouteObject} from "react-router-dom";
import {API_PAGE} from "./constants/routingConstants.ts";
import {WelcomePageChildRoutes} from "./pages/WelcomePage/WelcomePage.routes.tsx";
import {AnonRoute} from "./router/AnonRoute.tsx";
import {Navigate} from "react-router-dom";
import ProtectedRoute from "./router/ProtectedRoute.tsx";

export const routes: RouteObject[] = [
  {
    path: '/',
    element: <Navigate to={API_PAGE.Home} replace />
  },
  {
    path: API_PAGE.Auth,
    element: <AnonRoute />,
    children: WelcomePageChildRoutes,
  },
  {
    element: <ProtectedRoute />
  }
]

