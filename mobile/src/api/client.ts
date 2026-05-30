import axios from "axios";
import * as SecureStore from "expo-secure-store";
import { API_BASE_URL } from "@constants/config";

const TOKEN_KEY = "auth_token";

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: { "Content-Type": "application/json" },
});

apiClient.interceptors.request.use(async (config) => {
  const token = await SecureStore.getItemAsync(TOKEN_KEY);
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (!error.response) {
      return Promise.reject(new Error("Sunucuya ulaşılamıyor."));
    }

    const status = error.response.status;
    const contentType: string = error.response.headers?.["content-type"] ?? "";

    // API HTML döndürüyorsa (401/403 Spring default)
    if (contentType.includes("text/html")) {
      if (status === 401) return Promise.reject(new Error("Oturum açmanız gerekiyor."));
      if (status === 403) return Promise.reject(new Error("Bu işlem için yetkiniz yok."));
      return Promise.reject(new Error("Sunucu hatası."));
    }

    const data = error.response.data;
    const message = data?.message ?? data?.error ?? "Bir hata oluştu.";
    const apiError = new Error(message) as Error & { statusCode?: number; errorCode?: string; validationErrors?: unknown[] };
    apiError.statusCode = data?.statusCode;
    apiError.errorCode = data?.errorCode;
    apiError.validationErrors = data?.validationErrors;
    return Promise.reject(apiError);
  }
);
