import { MaterialCommunityIcons } from "@expo/vector-icons";
import { Tabs } from "expo-router";
import { Pressable } from "react-native";
import { useAuthStore } from "@/store/authStore";
import { appTheme } from "@/theme";

export default function AdminLayout() {
  const { logout } = useAuthStore();

  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: appTheme.colors.primary,
        tabBarInactiveTintColor: "#9e9e9e",
        headerStyle: { backgroundColor: appTheme.colors.primary },
        headerTintColor: "#ffffff",
        headerRight: () => (
          <Pressable onPress={logout} style={{ marginRight: 16 }}>
            <MaterialCommunityIcons name="logout" size={24} color="#ffffff" />
          </Pressable>
        ),
      }}
    >
      <Tabs.Screen
        name="index"
        options={{
          title: "Adminler",
          tabBarLabel: "Adminler",
          tabBarIcon: ({ color, size }) => (
            <MaterialCommunityIcons name="shield-account" size={size} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="sellers"
        options={{
          title: "Satıcılar",
          tabBarLabel: "Satıcılar",
          tabBarIcon: ({ color, size }) => (
            <MaterialCommunityIcons name="store" size={size} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="users"
        options={{
          title: "Kullanıcılar",
          tabBarLabel: "Kullanıcılar",
          href: null,
        }}
      />
    </Tabs>
  );
}
