import { apiClient } from "./client";

export type AdminResponse = {
  id: number;
  username: string;
  email: string;
};

export async function getAdmins(): Promise<AdminResponse[]> {
  const response = await apiClient.get<AdminResponse[]>("/api/admin/admins");
  return response.data;
}
