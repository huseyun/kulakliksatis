import { router, useFocusEffect } from "expo-router";
import { useCallback, useState } from "react";
import { FlatList, StyleSheet, View } from "react-native";
import { FAB, Text } from "react-native-paper";
import { getAdmins, AdminResponse } from "@/api/admins";
import ErrorMessage from "@/components/ui/ErrorMessage";
import LoadingScreen from "@/components/ui/LoadingScreen";
import UserCard from "@/components/features/UserCard";

export default function AdminListScreen() {
  const [admins, setAdmins] = useState<AdminResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useFocusEffect(
    useCallback(() => {
      loadAdmins();
    }, [])
  );

  const loadAdmins = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getAdmins();
      setAdmins(data);
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Adminler yüklenemedi.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingScreen />;

  return (
    <View style={styles.container}>
      {error && <ErrorMessage message={error} />}
      <FlatList
        data={admins}
        keyExtractor={(item) => String(item.id)}
        renderItem={({ item }) => (
          <UserCard
            user={{ id: item.id, username: item.username, email: item.email, userType: [{ id: 0, type: "ADMIN" }] }}
            onPress={() => router.push(`/(admin)/users/${item.id}`)}
          />
        )}
        ListEmptyComponent={
          <View style={styles.empty}>
            <Text style={styles.emptyText}>Admin bulunamadı.</Text>
          </View>
        }
        contentContainerStyle={styles.list}
      />
      <FAB icon="plus" style={styles.fab} onPress={() => router.push("/(admin)/sellers/create")} label="Satıcı Ekle" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#f5f5f5" },
  list: { paddingTop: 16, paddingBottom: 80 },
  empty: { alignItems: "center", marginTop: 60 },
  emptyText: { color: "#9ca3af" },
  fab: { position: "absolute", bottom: 24, right: 16, backgroundColor: "#ff6f00" },
});
