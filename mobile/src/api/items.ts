import { apiClient } from "./client";

export type Image = {
  originalKey?: string;
  thumbnailKey?: string;
  standardKey?: string;
  isThumbnail?: boolean;
  displayOrder?: number;
};

export type ItemSummaryResponse = {
  id: number;
  title: string;
  price: number | null;
  isRecommended: boolean;
  thumbnailImageUrl: string | null;
};

export type ItemResponse = {
  id: number;
  title: string;
  price: number | null;
  description: string | null;
  seller: { id: number; username: string; email: string; companyName: string | null };
  images: Image[];
  autoeqId: string | null;
};

export type CreateItemRequest = {
  name: string;
  title: string;
  brand: string;
  description?: string;
};

export type UpdateItemRequest = CreateItemRequest;

export async function getRecommended(): Promise<ItemSummaryResponse[]> {
  const response = await apiClient.get<ItemSummaryResponse[]>("/api/items/recommended");
  return response.data;
}

export async function getItem(id: number): Promise<ItemResponse> {
  const response = await apiClient.get<ItemResponse>(`/api/items/${id}`);
  return response.data;
}

export async function createItem(data: CreateItemRequest): Promise<void> {
  await apiClient.post("/api/items", data);
}

export async function updateItem(id: number, data: UpdateItemRequest): Promise<void> {
  await apiClient.put(`/api/items/${id}`, data);
}

export async function deleteItem(id: number): Promise<void> {
  await apiClient.delete(`/api/items/${id}`);
}

export async function uploadItemImages(
  itemId: number,
  files: Array<{ uri: string; name: string; type: string }>,
  isThumbnail?: boolean[]
): Promise<void> {
  const formData = new FormData();
  files.forEach((file) => {
    formData.append("files", { uri: file.uri, name: file.name, type: file.type } as unknown as Blob);
  });

  const params = isThumbnail?.length
    ? "?" + isThumbnail.map((v) => `isThumbnail=${v}`).join("&")
    : "";

  await apiClient.post(`/api/items/${itemId}/images${params}`, formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
}
