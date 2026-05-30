import { jwtDecode } from "jwt-decode";

type JwtPayload = {
  sub: string;
  roles: string[];
  iat: number;
  exp: number;
};

export type UserRole = "ADMIN" | "SELLER" | "SHOPPER";

export function decodeToken(token: string): JwtPayload | null {
  try {
    return jwtDecode<JwtPayload>(token);
  } catch {
    return null;
  }
}

export function extractRole(token: string): UserRole | null {
  const payload = decodeToken(token);
  if (!payload?.roles?.length) return null;

  if (payload.roles.includes("ROLE_ADMIN")) return "ADMIN";
  if (payload.roles.includes("ROLE_SELLER")) return "SELLER";
  if (payload.roles.includes("ROLE_SHOPPER")) return "SHOPPER";
  return null;
}

export function isTokenExpired(token: string): boolean {
  const payload = decodeToken(token);
  if (!payload) return true;
  return Date.now() / 1000 > payload.exp;
}

export function extractUsername(token: string): string | null {
  return decodeToken(token)?.sub ?? null;
}
