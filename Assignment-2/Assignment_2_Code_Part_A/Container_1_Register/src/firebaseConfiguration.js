import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";

const firebaseConfiguration = {
    apiKey: "AIzaSyBhCLGavboUIbeMAOMOKHyWLSkH2K4e86Y",
    authDomain: "csci5410-68706.firebaseapp.com",
    projectId: "csci5410-68706",
    storageBucket: "csci5410-68706.appspot.com",
    messagingSenderId: "96039592931",
    appId: "1:96039592931:web:c87026671358a677d576d6"
};

const app = initializeApp(firebaseConfiguration);
const firestoreDatabase = getFirestore(app);

export default firestoreDatabase