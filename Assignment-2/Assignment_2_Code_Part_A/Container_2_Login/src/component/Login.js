import React, { useState } from "react";
import firestoreDatabase from "../firebaseConfiguration";
import { Button } from "@material-ui/core";
import {
  collection,
  query,
  where,
  doc,
  getDocs,
  updateDoc,
} from "firebase/firestore";
import "../css/Login.css";

const Login = () => {

  const [errors, setErrors] = useState({});
  const [data, updateData] = useState({
    email: "",
    password: "",
  });

  const onChange = (e) => {
    updateData({
      ...data,
      [e.target.name]: e.target.value,
    });
  };
  
  const loginUser = async () => {
    try {
      const queryObj = query(
        collection(firestoreDatabase, "users"),
        where("email", "==", data.email)
      );
      const querySnapshot = await getDocs(queryObj);
      querySnapshot.forEach(async (userDoc) => {
        if (data.password === userDoc.data().password) {
          querySnapshot.forEach(async (userDoc) => {
            if (userDoc.data().status) {
              console.log(userDoc.id, " => ", userDoc.data());
              const userState = doc(firestoreDatabase, "users", userDoc.id);
              await updateDoc(userState, {
                status: "online",
              });
              window.location.href = "https://container-3-anmmfspxoa-ue.a.run.app/?email=" + userDoc.data().email +"&updateid="+userDoc.id;
            }
          });
        }
        updateData({
          email: "",
          password: "",
        });
      });
    } catch (e) {
      console.error("Error while fetching data ", e);
    }
  };

  const onSubmit = (e) => {
    e.preventDefault();
    let errors = validate(data)
    setErrors(validate(data));
    console.log(Object.keys(errors).length)
    if (Object.keys(errors).length === 0) {
      loginUser();
    }
  };

  function validate (data) {
    let errors = {};

    if (!data.email) {
      errors.email = 'Email required';
    }

    if (!data.password) {
      errors.password = 'Password required';
    }
    return errors;
  }
  return (
    <div className="form-container">
      <div className="form-content-right">
        <form className="form" onSubmit={onSubmit}>
          <h1>Login</h1>
          <div className="form-inputs">
            <input
              type="text"
              className="form-input"
              name="email"
              placeholder="Enter your email"
              value={data.email}
              onChange={onChange}
            />
            {errors.email && <p class="error">{errors.email}</p>}
          </div>
          <div className="form-inputs">
            <input
              type="password"
              className="form-input"
              name="password"
              placeholder="Enter your password"
              value={data.password}
              onChange={onChange}
            />
            {errors.password && <p class="error">{errors.password}</p>}
          </div>
          <Button className="form-input-btn" color="primary" variant="contained" type="submit">
            Submit
          </Button>
        </form>
      </div>
    </div>
  );
};

export default Login;
