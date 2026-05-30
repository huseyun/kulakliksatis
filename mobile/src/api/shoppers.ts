import { apiClient } from "./client";

export type ShopperResponse = {
  id: number;
  username: string;
  email: string;
  firstName: string | null;
  lastName: string | null;
};

export type UpdateShopperRequest = { firstName: string; lastName: string };

export async function getMe(): Promise<ShopperResponse> {
  const response = await apiClient.get<ShopperResponse>("/api/shoppers/me");
  return response.data;
}

export async function updateMe(data: UpdateShopperRequest): Promise<void> {
  await apiClient.put("/api/shoppers/me", data);
}
