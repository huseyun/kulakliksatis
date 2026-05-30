import { router, Slot } from "expo-router";
import { useEffect } from "react";
import { PaperProvider } from "react-native-paper";
import { SafeAreaProvider } from "react-native-safe-area-context";
import { useAuthStore } from "@/store/authStore";
import { appTheme } from "@/theme";
import LoadingScreen from "@/components/ui/LoadingScreen";

export default function RootLayout() {
  const { token, role, isLoading, loadStoredToken } = useAuthStore();

  useEffect(() => {
    loadStoredToken();
  }, []);

  useEffect(() => {
    if (isLoading) return;
    if (!token) {
      router.replace("/(auth)/login");
    } else if (role === "ADMIN") {
      router.replace("/(admin)/");
    } else if (role === "SELLER") {
      router.replace("/(seller)/");
    } else if (role === "SHOPPER") {
      router.replace("/(shopper)/");
    }
  }, [isLoading, token, role]);

  if (isLoading) return <LoadingScreen />;

  return (
    <SafeAreaProvider>
      <PaperProvider theme={appTheme}>
        <Slot />
      </PaperProvider>
    </SafeAreaProvider>
  );
}
