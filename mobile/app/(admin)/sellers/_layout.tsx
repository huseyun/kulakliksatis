import { Stack } from "expo-router";
import { appTheme } from "@/theme";

export default function SellersLayout() {
  return (
    <Stack
      screenOptions={{
        headerStyle: { backgroundColor: appTheme.colors.primary },
        headerTintColor: "#ffffff",
      }}
    >
      <Stack.Screen name="index" options={{ title: "Satıcılar" }} />
      <Stack.Screen name="[id]" options={{ title: "Satıcı Detayı" }} />
      <Stack.Screen name="create" options={{ title: "Satıcı Oluştur" }} />
    </Stack>
  );
}
