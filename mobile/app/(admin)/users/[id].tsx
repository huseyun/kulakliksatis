import { router, useLocalSearchParams } from "expo-router";
import { useEffect, useState } from "react";
import { ScrollView, StyleSheet, View } from "react-native";
import { Button, Card, Dialog, Divider, Portal, Text } from "react-native-paper";
import { deleteUser, getUserById, UserResponse } from "@/api/users";
import ErrorMessage from "@/components/ui/ErrorMessage";
import LoadingScreen from "@/components/ui/LoadingScreen";

export default function UserDetailScreen() {
  const { id } = useLocalSearchParams<{ id: string }>();
  const [user, setUser] = useState<UserResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [deleteDialog, setDeleteDialog] = useState(false);
  const [deleting, setDeleting] = useState(false);

  useEffect(() => {
    loadUser();
  }, [id]);

  const loadUser = async () => {
    if (!id) return;
    setLoading(true);
    setError(null);
    try {
      const data = await getUserById(Number(id));
      setUser(data);
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Kullanıcı yüklenemedi.");
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async () => {
    if (!id) return;
    setDeleting(true);
    try {
      await deleteUser(Number(id));
      router.back();
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Silme başarısız.");
      setDeleteDialog(false);
    } finally {
      setDeleting(false);
    }
  };

  if (loading) return <LoadingScreen />;

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      {error && <ErrorMessage message={error} />}
      {user && (
        <>
          <Card style={styles.card}>
            <Card.Content>
              <Text variant="headlineSmall" style={styles.username}>@{user.username}</Text>
              <Divider style={styles.divider} />
              <View style={styles.row}>
                <Text variant="bodySmall" style={styles.label}>E-posta</Text>
                <Text variant="bodyMedium">{user.email}</Text>
              </View>
              <View style={styles.row}>
                <Text variant="bodySmall" style={styles.label}>Roller</Text>
                <Text variant="bodyMedium">
                  {user.userType?.map(t => t.type).join(", ") || "—"}
                </Text>
              </View>
              <View style={styles.row}>
                <Text variant="bodySmall" style={styles.label}>ID</Text>
                <Text variant="bodyMedium">#{user.id}</Text>
              </View>
            </Card.Content>
          </Card>

          <Button
            mode="contained"
            icon="delete"
            buttonColor="#b91c1c"
            onPress={() => setDeleteDialog(true)}
            style={styles.deleteButton}
            contentStyle={styles.buttonContent}
          >
            Kullanıcıyı Sil
          </Button>
        </>
      )}

      <Portal>
        <Dialog visible={deleteDialog} onDismiss={() => setDeleteDialog(false)}>
          <Dialog.Title>Kullanıcıyı Sil</Dialog.Title>
          <Dialog.Content>
            <Text>@{user?.username} kullanıcısını silmek istediğinize emin misiniz? Bu işlem geri alınamaz.</Text>
          </Dialog.Content>
          <Dialog.Actions>
            <Button onPress={() => setDeleteDialog(false)}>İptal</Button>
            <Button textColor="#b91c1c" onPress={handleDelete} loading={deleting} disabled={deleting}>Sil</Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#f5f5f5" },
  content: { padding: 20 },
  card: { marginBottom: 20 },
  username: { color: "#1a237e", fontWeight: "bold", marginBottom: 4 },
  divider: { marginVertical: 12 },
  row: { marginBottom: 12 },
  label: { color: "#9ca3af", marginBottom: 2 },
  deleteButton: { marginTop: 8 },
  buttonContent: { paddingVertical: 4 },
});
