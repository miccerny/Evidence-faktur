import { useNavigate } from "react-router-dom";
import InputField from "../components/InputField";
import { useState } from "react";
import { apiPost} from "../utils/api";
import FlashMessage from "../components/FlashMessage";
import HttpRequestError from "../errors/HttpRequestError";

const RegistrationPage = () => {

    const nav = useNavigate();
    const [errorMessageState, setErrorMessageState] = useState(null);
    const [valueState, setValueState] = useState({ password: "", confirmPassword: "", email: "" });
    const [successState, setSuccess] = useState(false);
    const [sentState, setSentState] = useState(false)

    const handleSubmit = (e) => {
        e.preventDefault();
        if (valueState.password !== valueState.confirmPassword) {
            setErrorMessageState("Hesla se nerovnají");
            return;
        }

        const { confirmPassword, ...registrationData } = valueState;
        apiPost("/api/user", registrationData)
            .then(() => {
                setSentState(true);
                setSuccess(true);
                nav("/login");
            }).catch(e => {
                if (e instanceof HttpRequestError && e.status === 400) {
                    setErrorMessageState(e.message);
                    setSentState(true);
                    setSuccess(false);
                    return;
                }
                setErrorMessageState("Při komunikaci se serverem nastala chyba.");
            });
    };

    const handleChange = (e) => {
        const fieldName = e.target.name;
        setValueState({ ...valueState, [fieldName]: e.target.value });
    }

    const sent = sentState;
    const success = successState;
    return (
        <div >
            {/* Flash message po odeslání formuláře */}
            {sent && (
                <FlashMessage
                    theme={success ? "success" : ""}
                    text={success ? "Registrace uživatele proběhla úspěšně." : ""}
                />
            )}
            {/* Pokud se načítají data z backendu, zobrazí se informační zpráva*/}
{sent ? (
                <div className="alert alert-danger">{errorMessageState}</div>
            ) : null}
            <h1>Registrace uživatele</h1>
            <hr />
            <form onSubmit={handleSubmit}>
                <InputField
                    type="email"
                    name="email"
                    label="Email"
                    prompt="Zadejte váš Email"
                    value={valueState.email}
                    handleChange={handleChange}
                />
                <InputField
                    type="password"
                    name="password"
                    label="Heslo"
                    prompt="Zadejte vaše Heslo"
                    min={6}
                    value={valueState.password}
                    handleChange={handleChange}
                />
                <InputField
                    type="password"
                    name="confirmPassword"
                    label="Heslo znovu"
                    prompt="Zadejte Vaše heslo znovu"
                    value={valueState.confirmPassword}
                    handleChange={handleChange}
                />
                <input type="submit" className="btn btn-primary mt-2" value="Registrovat se" />
            </form>
        </div>
    );
}
export default RegistrationPage;