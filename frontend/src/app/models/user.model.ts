export interface User {
  id: number;
  profile: {
    firstName?: string;
    lastName?: string;
    email?: string;
    phone?: string;
  };
  admin: boolean;
  active: boolean;
  status: 'JOINED' | 'PENDING';
}
