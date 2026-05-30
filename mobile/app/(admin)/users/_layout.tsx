import { Stack } from "expo-router";
import { appTheme } from "@/theme";

export default function UsersLayout() {
  return (
    <Stack
      screenOptions={{
        headerStyle: { backgroundColor: appTheme.colors.primary },
        headerTintColor: "#ffffff",
      }}
    >
      <Stack.Screen name="[id]" options={{ title: "Kullanıcı Detayı" }} />
    </Stack>
  );
}
