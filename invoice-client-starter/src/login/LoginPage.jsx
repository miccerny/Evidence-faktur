import { useNavigate } from "react-router-dom";
import FlashMessage from "../components/FlashMessage";
import InputField from "../components/InputField";
import { useSession } from "../contexts/session";
import { useEffect, useState } from "react";
import { apiPost } from "../utils/api";
import HttpRequestError from "../errors/HttpRequestError";


const LoginPage = () => {

    const [valuesState, setValuesState] = useState({ email: "", password: "" });
    const { session, setSession } = useSession();
    const navigate = useNavigate();
    const [emailError, setEmailError] = useState("");
    const [passwordError, setPasswordError] = useState("");

    useEffect(() => {
        if (session.status === "authenticated") {
            navigate("/");
        }
    }, [session.status]);

    const handleChange = (e) => {
        const fieldName = e.target.name;
        setValuesState({ ...valuesState, [fieldName]: e.target.value });

        if (fieldName === "email") setEmailError("");
        if (fieldName === "password") setPasswordError("");
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        apiPost("/api/auth", valuesState)
            .then(data => {
                setSession({ data, status: "authenticated" });
                navigate("/");
            })
            .catch((e) => {
                if (e instanceof HttpRequestError) {

                    if (e.data?.error === "EMAIL_NOT_FOUND") {
                        setEmailError(e.data.message);
                        setPasswordError("");
                    } else if (e.data?.error === "BAD_PASSWORD") {
                        setPasswordError(e.data.message);
                        setEmailError("");
                    } else {
                        setEmailError("");
                        setPasswordError(e.message || "Neznámá chyba při přihlášení");
                    }

                } else {
                    setEmailError("");
                    setPasswordError("Chyba při komunikaci se serverem");

                }
            });
    }

    return (
        <div>
            <h1>Přihlášení</h1>
            <form onSubmit={handleSubmit}>

                <InputField
                    type="email"
                    required={true}
                    label="E-mail"
                    handleChange={handleChange}
                    value={valuesState.email}
                    prompt="E-mail"
                    name="email" />
                {emailError && <div className="text-danger">{emailError}</div>}
                <InputField
                    type="password"
                    required={true}
                    label="Heslo"
                    handleChange={handleChange}
                    value={valuesState.password}
                    prompt={"Heslo"}
                    name="password" />
                {passwordError && <div className="text-danger">{passwordError}</div>}
                <input type="submit" className="btn btn-primary mt-2" value="Přihlásit se" />
            </form>
        </div>
    );
}

export default LoginPage;