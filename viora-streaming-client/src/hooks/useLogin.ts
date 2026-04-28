import {useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import {API_PAGE} from "../constants/routingConstants.ts";

const isTokenExpired = (token: string): boolean => {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.exp * 1000 < Date.now();
  } catch {
    return true;
  }
};

const useLogin = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('JWT_TOKEN');

    if (!token || isTokenExpired(token)) {
      localStorage.removeItem('JWT_TOKEN');
      navigate(API_PAGE.Auth, {replace: true});
    }
  }, [navigate]);
};

export default useLogin;