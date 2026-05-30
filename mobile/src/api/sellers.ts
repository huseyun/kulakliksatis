import { apiClient } from "./client";
import { ItemSummaryResponse } from "./items";

export type SellerResponse = {
  id: number;
  username: string;
  email: string;
  companyName: string | null;
};

export type SellerDetailedResponse = SellerResponse & {
  items: ItemSummaryResponse[];
};

export type CreateSellerRequest = {
  username: string;
  password: string;
  email: string;
  companyName?: string;
};

export type UpdateSellerRequest = { companyName: string };

// Admin: list all sellers
export async function getSellers(): Promise<SellerResponse[]> {
  const response = await apiClient.get<SellerResponse[]>("/api/admin/sellers");
  return response.data;
}

// Admin: seller detail (includes items in response)
export async function getSellerById(id: number): Promise<SellerDetailedResponse> {
  const response = await apiClient.get<SellerDetailedResponse>(`/api/admin/sellers/${id}`);
  return response.data;
}

// Admin: seller's items separately
export async function getSellerItems(sellerId: number): Promise<ItemSummaryResponse[]> {
  const response = await apiClient.get<ItemSummaryResponse[]>(`/api/admin/sellers/${sellerId}/items`);
  return response.data;
}

// Admin: create seller
export async function createSeller(data: CreateSellerRequest): Promise<void> {
  await apiClient.post("/api/admin/sellers", data);
}

// Seller: update own company info
export async function updateSeller(data: UpdateSellerRequest): Promise<void> {
  await apiClient.put("/api/sellers", data);
}

// Seller: get own items
export async function getMyItems(): Promise<ItemSummaryResponse[]> {
  const response = await apiClient.get<ItemSummaryResponse[]>("/api/sellers/me/items");
  return response.data;
}
