import { useFocusEffect } from "expo-router";
import { useCallback, useState } from "react";
import { KeyboardAvoidingView, Platform, ScrollView, StyleSheet, View } from "react-native";
import { Button, Dialog, Divider, Portal, Text, TextInput } from "react-native-paper";
import { updateSeller } from "@/api/sellers";
import { updatePassword } from "@/api/password";
import ErrorMessage from "@/components/ui/ErrorMessage";
import LoadingScreen from "@/components/ui/LoadingScreen";
import { useAuthStore } from "@/store/authStore";
import * as SecureStore from "expo-secure-store";

export default function SellerProfileScreen() {
  const { username, logout } = useAuthStore();

  const [companyName, setCompanyName] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [successMsg, setSuccessMsg] = useState<string | null>(null);

  const [passwordDialogVisible, setPasswordDialogVisible] = useState(false);
  const [newPassword, setNewPassword] = useState("");
  const [passwordLoading, setPasswordLoading] = useState(false);
  const [passwordError, setPasswordError] = useState<string | null>(null);

  const handleSave = async () => {
    if (!companyName.trim()) {
      setError("Şirket adı boş bırakılamaz.");
      return;
    }
    setLoading(true);
    setError(null);
    setSuccessMsg(null);
    try {
      await updateSeller({ companyName: companyName.trim() });
      setSuccessMsg("Şirket bilgileri güncellendi.");
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Güncelleme başarısız.");
    } finally {
      setLoading(false);
    }
  };

  const handlePasswordChange = async () => {
    if (newPassword.length < 8) {
      setPasswordError("Şifre en az 8 karakter olmalıdır.");
      return;
    }
    setPasswordLoading(true);
    setPasswordError(null);
    try {
      await updatePassword(newPassword);
      setPasswordDialogVisible(false);
      setNewPassword("");
      setSuccessMsg("Şifre değiştirildi.");
    } catch (e: unknown) {
      setPasswordError(e instanceof Error ? e.message : "Şifre değiştirilemedi.");
    } finally {
      setPasswordLoading(false);
    }
  };

  return (
    <KeyboardAvoidingView style={styles.flex} behavior={Platform.OS === "ios" ? "padding" : undefined}>
      <ScrollView contentContainerStyle={styles.scroll} keyboardShouldPersistTaps="handled">
        <View style={styles.container}>
          <View style={styles.infoBox}>
            <Text variant="titleMedium" style={styles.infoName}>{username ?? "Satıcı"}</Text>
            <Text variant="bodySmall" style={styles.infoSub}>Satıcı Hesabı</Text>
          </View>

          <Text variant="titleSmall" style={styles.sectionTitle}>Firma Bilgileri</Text>
          <TextInput
            label="Şirket Adı"
            value={companyName}
            onChangeText={setCompanyName}
            mode="outlined"
            style={styles.input}
            left={<TextInput.Icon icon="store" />}
            placeholder="Şirket adınızı girin"
          />

          {error && <ErrorMessage message={error} />}
          {successMsg && (
            <View style={styles.successBox}>
              <Text style={styles.successText}>{successMsg}</Text>
            </View>
          )}

          <Button mode="contained" onPress={handleSave} loading={loading} disabled={loading} style={styles.saveButton} contentStyle={styles.buttonContent}>
            Kaydet
          </Button>

          <Divider style={styles.divider} />

          <Text variant="titleSmall" style={styles.sectionTitle}>Güvenlik</Text>
          <Button mode="outlined" icon="lock-reset" onPress={() => { setPasswordDialogVisible(true); setPasswordError(null); setNewPassword(""); }} style={styles.passwordButton}>
            Şifre Değiştir
          </Button>

          <Divider style={styles.divider} />

          <Button mode="text" icon="logout" textColor="#b91c1c" onPress={logout}>
            Çıkış Yap
          </Button>
        </View>
      </ScrollView>

      <Portal>
        <Dialog visible={passwordDialogVisible} onDismiss={() => setPasswordDialogVisible(false)}>
          <Dialog.Title>Şifre Değiştir</Dialog.Title>
          <Dialog.Content>
            <TextInput label="Yeni Şifre" value={newPassword} onChangeText={setNewPassword} secureTextEntry mode="outlined" />
            {passwordError && <ErrorMessage message={passwordError} />}
          </Dialog.Content>
          <Dialog.Actions>
            <Button onPress={() => setPasswordDialogVisible(false)}>İptal</Button>
            <Button onPress={handlePasswordChange} loading={passwordLoading} disabled={passwordLoading}>Değiştir</Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  flex: { flex: 1, backgroundColor: "#f5f5f5" },
  scroll: { flexGrow: 1 },
  container: { padding: 20 },
  infoBox: { backgroundColor: "#1a237e", borderRadius: 12, padding: 20, marginBottom: 24, alignItems: "center" },
  infoName: { color: "#ffffff", fontWeight: "bold" },
  infoSub: { color: "#c5cae9", marginTop: 4 },
  sectionTitle: { color: "#1a237e", marginBottom: 12, fontWeight: "bold" },
  input: { marginBottom: 12 },
  saveButton: { marginTop: 4 },
  buttonContent: { paddingVertical: 4 },
  divider: { marginVertical: 24 },
  passwordButton: { borderColor: "#1a237e" },
  successBox: { backgroundColor: "#dcfce7", borderRadius: 8, padding: 12, marginTop: 4 },
  successText: { color: "#166534", fontSize: 14 },
});
