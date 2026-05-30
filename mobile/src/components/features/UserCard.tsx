import React from "react";
import { Pressable, StyleSheet } from "react-native";
import { Card, Text } from "react-native-paper";
import { UserResponse } from "@/api/users";

type Props = { user: UserResponse; onPress?: () => void };

export default function UserCard({ user, onPress }: Props) {
  const roles = user.userType?.map((t) => t.type).join(", ") ?? "";
  return (
    <Pressable onPress={onPress} style={styles.wrapper}>
      <Card elevation={1}>
        <Card.Content>
          <Text variant="titleMedium">@{user.username}</Text>
          <Text variant="bodySmall" style={styles.email}>{user.email}</Text>
          {roles ? <Text variant="bodySmall" style={styles.role}>{roles}</Text> : null}
        </Card.Content>
      </Card>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  wrapper: { marginHorizontal: 16, marginBottom: 12 },
  email: { color: "#6b7280", marginTop: 4 },
  role: { color: "#1a237e", marginTop: 4 },
});
