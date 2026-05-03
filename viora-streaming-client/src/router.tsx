import type {RouteObject} from "react-router-dom";
import {API_PAGE} from "./constants/routingConstants.ts";
import {WelcomePageChildRoutes} from "./pages/WelcomePage/WelcomePage.routes.tsx";
import {AnonRoute} from "./router/AnonRoute.tsx";
import {Navigate} from "react-router-dom";
import ProtectedRoute from "./router/ProtectedRoute.tsx";
import {HomePageChildRoutes} from "./pages/HomePage/HomePage.routes.tsx";
import {MoviesPageChildRoutes} from "./pages/MoviesPage/MoviesPage.routes.tsx";
import {HistoryPage} from "./pages/HistoryPage/HistoryPage.tsx";

export const routes: RouteObject[] = [
  {
    path: '/',
    element: <Navigate to={API_PAGE.Home} replace/>
  },
  {
    path: API_PAGE.Auth,
    element: <AnonRoute/>,
    children: WelcomePageChildRoutes,
  },
  {
    element: <ProtectedRoute/>,
    children: [
      {
        path: API_PAGE.Movies,
        children: MoviesPageChildRoutes,
      },
      {
        path: API_PAGE.Home,
        children: HomePageChildRoutes,
      },
      {
        path: API_PAGE.History,
        element: <HistoryPage/>
      }
    ]
  }
]

