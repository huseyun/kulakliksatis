import { Stack } from "expo-router";
import { appTheme } from "@/theme";

export default function ProductsLayout() {
  return (
    <Stack
      screenOptions={{
        headerStyle: { backgroundColor: appTheme.colors.primary },
        headerTintColor: "#ffffff",
      }}
    >
      <Stack.Screen name="create" options={{ title: "Ürün Ekle" }} />
      <Stack.Screen name="[id]" options={{ title: "Ürün Detayı" }} />
    </Stack>
  );
}
