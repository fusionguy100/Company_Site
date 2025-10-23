export interface UserRequestDto {
  credentials: CredentialsDto;
  profile: ProfileDto;
  isAdmin: boolean;
  companyId: number;
}

export interface CredentialsDto {
  username: string;
  password: string;
}

export interface ProfileDto {
  firstName: string;
  lastName: string;
  email: string;
  phone?: string;
}
