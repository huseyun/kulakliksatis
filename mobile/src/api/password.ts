import { apiClient } from "./client";

export async function updatePassword(password: string): Promise<void> {
  await apiClient.put("/api/users", { password });
}
