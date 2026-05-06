export interface Account {
  email: string;
  fullName: string;
  bio: string;
}

export interface UpdateAccountRequest {
  fullName: string;
  bio: string;
}