import { Outlet } from "react-router-dom";
import { Header } from "./components/Header/Header.tsx";

export function AppLayout() {
  return (
      <>
        <Header />
        <Outlet />
      </>
  );
}