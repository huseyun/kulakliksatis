import { MaterialCommunityIcons } from "@expo/vector-icons";
import { Tabs } from "expo-router";
import { appTheme } from "@/theme";

export default function SellerLayout() {
  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: appTheme.colors.primary,
        tabBarInactiveTintColor: "#9e9e9e",
        headerStyle: { backgroundColor: appTheme.colors.primary },
        headerTintColor: "#ffffff",
      }}
    >
      <Tabs.Screen
        name="index"
        options={{
          title: "Ürünlerim",
          tabBarLabel: "Ürünlerim",
          tabBarIcon: ({ color, size }) => (
            <MaterialCommunityIcons name="headphones" size={size} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="profile"
        options={{
          title: "Profil",
          tabBarLabel: "Profil",
          tabBarIcon: ({ color, size }) => (
            <MaterialCommunityIcons name="account" size={size} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="products"
        options={{ href: null }}
      />
    </Tabs>
  );
}
