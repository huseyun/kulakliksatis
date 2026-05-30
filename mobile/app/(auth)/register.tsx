import { router } from "expo-router";
import { useState } from "react";
import { KeyboardAvoidingView, Platform, ScrollView, StyleSheet, View } from "react-native";
import { Button, Text, TextInput } from "react-native-paper";
import { register } from "@/api/auth";
import ErrorMessage from "@/components/ui/ErrorMessage";

export default function RegisterScreen() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [showPassword, setShowPassword] = useState(false);

  const handleRegister = async () => {
    if (!username.trim() || !email.trim() || !password.trim()) {
      setError("Tüm alanlar zorunludur.");
      return;
    }
    setLoading(true);
    setError(null);
    try {
      await register({ username: username.trim(), email: email.trim(), password });
      router.replace("/(auth)/login");
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Kayıt başarısız.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <KeyboardAvoidingView style={styles.flex} behavior={Platform.OS === "ios" ? "padding" : undefined}>
      <ScrollView contentContainerStyle={styles.scroll} keyboardShouldPersistTaps="handled">
        <View style={styles.container}>
          <View style={styles.header}>
            <Text variant="headlineSmall" style={styles.title}>Hesap Oluştur</Text>
            <Text variant="bodyMedium" style={styles.subtitle}>Yeni bir shopper hesabı oluşturun</Text>
          </View>

          <TextInput label="Kullanıcı Adı" value={username} onChangeText={setUsername} autoCapitalize="none" autoCorrect={false} mode="outlined" style={styles.input} left={<TextInput.Icon icon="account" />} />
          <TextInput label="E-posta" value={email} onChangeText={setEmail} autoCapitalize="none" keyboardType="email-address" mode="outlined" style={styles.input} left={<TextInput.Icon icon="email" />} />
          <TextInput label="Şifre" value={password} onChangeText={setPassword} secureTextEntry={!showPassword} mode="outlined" style={styles.input} left={<TextInput.Icon icon="lock" />} right={<TextInput.Icon icon={showPassword ? "eye-off" : "eye"} onPress={() => setShowPassword(v => !v)} />} />

          {error && <ErrorMessage message={error} />}

          <Button mode="contained" onPress={handleRegister} loading={loading} disabled={loading} style={styles.button} contentStyle={styles.buttonContent}>
            Kayıt Ol
          </Button>
          <Button mode="text" onPress={() => router.back()} style={styles.backButton}>
            Zaten hesabınız var mı? Giriş yapın
          </Button>
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  flex: { flex: 1, backgroundColor: "#f5f5f5" },
  scroll: { flexGrow: 1 },
  container: { flex: 1, paddingHorizontal: 24, paddingVertical: 48, justifyContent: "center" },
  header: { alignItems: "center", marginBottom: 32 },
  title: { color: "#1a237e", fontWeight: "bold" },
  subtitle: { color: "#9e9e9e", marginTop: 4 },
  input: { marginBottom: 16 },
  button: { marginTop: 8 },
  buttonContent: { paddingVertical: 4 },
  backButton: { marginTop: 8 },
});
