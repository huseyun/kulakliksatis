import * as SecureStore from "expo-secure-store";
import { create } from "zustand";
import { UserRole, extractRole, extractUsername, isTokenExpired } from "@/utils/jwt";

const TOKEN_KEY = "auth_token";

type AuthState = {
  token: string | null;
  role: UserRole | null;
  username: string | null;
  isLoading: boolean;
  setToken: (token: string) => void;
  logout: () => Promise<void>;
  loadStoredToken: () => Promise<void>;
};

export const useAuthStore = create<AuthState>((set) => ({
  token: null,
  role: null,
  username: null,
  isLoading: true,

  setToken: (token: string) => {
    SecureStore.setItemAsync(TOKEN_KEY, token);
    set({
      token,
      role: extractRole(token),
      username: extractUsername(token),
    });
  },

  logout: async () => {
    await SecureStore.deleteItemAsync(TOKEN_KEY);
    set({ token: null, role: null, username: null });
  },

  loadStoredToken: async () => {
    const token = await SecureStore.getItemAsync(TOKEN_KEY);
    if (token && !isTokenExpired(token)) {
      set({
        token,
        role: extractRole(token),
        username: extractUsername(token),
        isLoading: false,
      });
    } else {
      if (token) await SecureStore.deleteItemAsync(TOKEN_KEY);
      set({ token: null, role: null, username: null, isLoading: false });
    }
  },
}));
