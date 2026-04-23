import type {RouteObject} from "react-router-dom";
import {API_PAGE} from "./constants/routingConstants.ts";
import {WelcomePageChildRoutes} from "./pages/WelcomePage/WelcomePage.routes.tsx";

export const routes: RouteObject[] = [
  {
    path: API_PAGE.Auth,
    children: WelcomePageChildRoutes,
  }
]

