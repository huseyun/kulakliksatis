import { apiClient } from "./client";

export type UserTypeResponse = { id: number; type: string };
export type UserResponse = {
  id: number;
  username: string;
  email: string;
  userType: UserTypeResponse[];
};

export async function getUserById(id: number): Promise<UserResponse> {
  const response = await apiClient.get<UserResponse>(`/api/admin/users/${id}`);
  return response.data;
}

export async function deleteUser(id: number): Promise<void> {
  await apiClient.delete(`/api/admin/users/${id}`);
}
