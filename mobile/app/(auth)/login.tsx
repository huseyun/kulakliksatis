import { router } from "expo-router";
import { useState } from "react";
import { KeyboardAvoidingView, Platform, ScrollView, StyleSheet, View } from "react-native";
import { Button, Text, TextInput } from "react-native-paper";
import { login } from "@/api/auth";
import ErrorMessage from "@/components/ui/ErrorMessage";
import { useAuthStore } from "@/store/authStore";

export default function LoginScreen() {
  const { setToken } = useAuthStore();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [showPassword, setShowPassword] = useState(false);

  const handleLogin = async () => {
    if (!username.trim() || !password.trim()) {
      setError("Kullanıcı adı ve şifre boş bırakılamaz.");
      return;
    }
    setLoading(true);
    setError(null);
    try {
      const token = await login({ username: username.trim(), password });
      setToken(token);
    } catch (e: unknown) {
      setError(e instanceof Error ? e.message : "Giriş başarısız.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <KeyboardAvoidingView
      style={styles.flex}
      behavior={Platform.OS === "ios" ? "padding" : undefined}
    >
      <ScrollView contentContainerStyle={styles.scroll} keyboardShouldPersistTaps="handled">
        <View style={styles.container}>
          {/* Logo */}
          <View style={styles.logoSection}>
            <View style={styles.logoCircle}>
              <Text style={styles.logoEmoji}>🎧</Text>
            </View>
            <Text variant="headlineMedium" style={styles.title}>KulaklıkSatış</Text>
            <Text variant="bodyMedium" style={styles.subtitle}>Hesabınıza giriş yapın</Text>
          </View>

          {/* Form */}
          <TextInput
            label="Kullanıcı Adı"
            value={username}
            onChangeText={setUsername}
            autoCapitalize="none"
            autoCorrect={false}
            mode="outlined"
            style={styles.input}
            left={<TextInput.Icon icon="account" />}
          />
          <TextInput
            label="Şifre"
            value={password}
            onChangeText={setPassword}
            secureTextEntry={!showPassword}
            mode="outlined"
            style={styles.input}
            left={<TextInput.Icon icon="lock" />}
            right={
              <TextInput.Icon
                icon={showPassword ? "eye-off" : "eye"}
                onPress={() => setShowPassword((v) => !v)}
              />
            }
          />

          {error && <ErrorMessage message={error} />}

          <Button mode="contained" onPress={handleLogin} loading={loading} disabled={loading} style={styles.loginButton} contentStyle={styles.loginButtonContent}>
            Giriş Yap
          </Button>

          <Button mode="text" onPress={() => router.push("/(auth)/register")} style={styles.registerButton}>
            Hesabınız yok mu? Kayıt olun
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
  logoSection: { alignItems: "center", marginBottom: 40 },
  logoCircle: { width: 80, height: 80, borderRadius: 40, backgroundColor: "#1a237e", alignItems: "center", justifyContent: "center", marginBottom: 16 },
  logoEmoji: { fontSize: 32 },
  title: { color: "#1a237e", fontWeight: "bold", textAlign: "center" },
  subtitle: { color: "#9e9e9e", marginTop: 4, textAlign: "center" },
  input: { marginBottom: 16 },
  loginButton: { marginTop: 24 },
  loginButtonContent: { paddingVertical: 4 },
  registerButton: { marginTop: 8 },
});
