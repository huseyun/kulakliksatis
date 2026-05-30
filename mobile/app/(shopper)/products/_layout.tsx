import { Stack } from "expo-router";
import { appTheme } from "@/theme";

export default function ShopperProductsLayout() {
  return (
    <Stack
      screenOptions={{
        headerStyle: { backgroundColor: appTheme.colors.primary },
        headerTintColor: "#ffffff",
      }}
    >
      <Stack.Screen name="[id]" options={{ title: "Ürün Detayı" }} />
    </Stack>
  );
}
