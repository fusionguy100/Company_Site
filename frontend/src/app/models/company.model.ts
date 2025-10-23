import { Announcement } from "./announvement.model";

export interface Company {
  id: number;
  name: string;
  address?: string;
  description?: string;
  announcements?: Announcement[];
}
