import React, { useState, useEffect } from "react";
import firestoreDatabase from "../firebaseConfiguration";
import {
  collection,
  query,
  doc,
  getDocs,
  updateDoc,
  where,
} from "firebase/firestore";
import { Button } from "@material-ui/core";
import "../css/UserProfile.css";

export const UserProfile = () => {
  let params = new URL(document.location).searchParams;
  const userEmail = params.get("email");
  const userUpdateID = params.get("updateid");
  const [allUsers, setUsers] = useState("");

  useEffect(() => {
    async function fetchData() {
      var onlineUser = "";
      var x = 1;
      const queryObject = query(
        collection(firestoreDatabase, "users"),
        where("status", "==", "online")
      );
      const querySnapshot = await getDocs(queryObject);
      querySnapshot.forEach(async (userDoc) => {
        if (userDoc.data().status === "online") {
          if (x === 1) {
            onlineUser = " <" + userDoc.data().email + "> ";
            x = 0;
          } else {
            onlineUser = onlineUser + " <" + userDoc.data().email + "> ";
          }
        }
      });
      setUsers(onlineUser);
    }
    fetchData();
  }, []);

  const logout = async () => {
          const userState = doc(firestoreDatabase, "users", userUpdateID);
          await updateDoc(userState, {
            status: "offline",
          });
          window.location.href = "https://container-2-anmmfspxoa-ue.a.run.app/";
  };

  return (
    <div>
      <h3>Hi <h2>{userEmail}</h2> you are logged in.....</h3>
      <h4>Here are the other users who are online </h4>
      <p>{allUsers}</p>
      <Button
        size="medium"
        color="primary"
        variant="contained"
        onClick={logout}
      >
        Logout
      </Button>
    </div>
  );
};
