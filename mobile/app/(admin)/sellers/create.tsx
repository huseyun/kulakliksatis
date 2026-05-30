import { router } from "expo-router";
import { useState } from "react";
import { KeyboardAvoidingView, Platform, ScrollView, StyleSheet, View } from "react-native";
import { Button, Text, TextInput } from "react-native-paper";
import { createSeller } from "@/api/sellers";
import ErrorMessage from "@/components/ui/ErrorMessage";

export default function CreateSellerScreen() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [companyName, setCompanyName] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [showPassword, setShowPassword] = useState(false);

  const handleCreate = async () => {
    if (!username.trim() || !email.trim() || !password.trim()) {
      setError("Kullanıcı adı, e-posta ve şifre zorunludur.");
      return;
    }
    setLoading(true);
    setError(null);
    try {
      await createSeller({
        username: username.trim(),
        email: email.trim(),
        password,
        companyName: companyName.trim() || undefined,
      });
      router.back();
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Satıcı oluşturulamadı.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <KeyboardAvoidingView style={styles.flex} behavior={Platform.OS === "ios" ? "padding" : undefined}>
      <ScrollView contentContainerStyle={styles.scroll} keyboardShouldPersistTaps="handled">
        <View style={styles.container}>
          <Text variant="titleMedium" style={styles.sectionTitle}>Hesap Bilgileri</Text>
          <TextInput label="Kullanıcı Adı" value={username} onChangeText={setUsername} autoCapitalize="none" autoCorrect={false} mode="outlined" style={styles.input} left={<TextInput.Icon icon="account" />} />
          <TextInput label="E-posta" value={email} onChangeText={setEmail} autoCapitalize="none" keyboardType="email-address" mode="outlined" style={styles.input} left={<TextInput.Icon icon="email" />} />
          <TextInput label="Şifre" value={password} onChangeText={setPassword} secureTextEntry={!showPassword} mode="outlined" style={styles.input} left={<TextInput.Icon icon="lock" />} right={<TextInput.Icon icon={showPassword ? "eye-off" : "eye"} onPress={() => setShowPassword(v => !v)} />} />

          <Text variant="titleMedium" style={[styles.sectionTitle, styles.sectionGap]}>Firma Bilgileri</Text>
          <TextInput label="Şirket Adı (opsiyonel)" value={companyName} onChangeText={setCompanyName} mode="outlined" style={styles.input} left={<TextInput.Icon icon="store" />} />

          {error && <ErrorMessage message={error} />}

          <Button mode="contained" onPress={handleCreate} loading={loading} disabled={loading} style={styles.button} contentStyle={styles.buttonContent}>
            Satıcı Oluştur
          </Button>
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  flex: { flex: 1, backgroundColor: "#f5f5f5" },
  scroll: { flexGrow: 1 },
  container: { padding: 20 },
  sectionTitle: { color: "#1a237e", fontWeight: "bold", marginBottom: 12 },
  sectionGap: { marginTop: 8 },
  input: { marginBottom: 12 },
  button: { marginTop: 8 },
  buttonContent: { paddingVertical: 4 },
});
