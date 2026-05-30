import { apiClient } from "./client";

export type LoginRequest = { username: string; password: string };
export type RegisterRequest = { username: string; password: string; email: string };

export async function login(data: LoginRequest): Promise<string> {
  const response = await apiClient.post<{ token: string }>("/api/auth/login", data);
  return response.data.token;
}

export async function register(data: RegisterRequest): Promise<void> {
  await apiClient.post("/api/auth/register", data);
}
