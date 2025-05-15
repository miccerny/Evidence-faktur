import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom"
import { apiGet, apiPost, apiPut } from "../utils/api";
import FlashMessage from "../components/FlashMessage";
import InputField from "../components/InputField";
import InputSelect from "../components/InputSelect";


const InvoiceForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [buyerListState, setBuyerList] = useState([]);
    const [sellerListSatte, setSellerList] = useState([]);
    const [invoice, setInvoice] = useState({
        invoiceNumber: "",
        seller: "",
        buyer: "",
        issued: "",
        dueDate: "",
        product: "",
        price: "",
        vat: "",
        note: ""
    });


    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState(null);

    useEffect(() => {
        if (id) {
            apiGet("/api/invoices/" + id)
            .then((data) => {
                setInvoice(data)
            });

        }
        apiGet("/api/persons").then((data) => {
            setBuyerList(data.content),
            setSellerList(data.content)
        });

    }, [id]);

    const handleSubmit = (e) => {
        e.preventDefault();

        (id ? apiPut("/api/invoices/" + id, invoice) : apiPost("/api/invoices", invoice))
            .then((data) => {
                setSent(true);
                setSuccess(true);
                navigate("/invoices");
            })
            .catch((error) => {
                console.log(error.message);
                setError(error.message);
                setSent(true);
                setSuccess(false);
            });
    };

    const sent = sentState;
    const success = successState;

    return (
        <div>
            <h1>{id ? "Upravit" : "Vytvořit"} fakturu</h1>
            <hr />
            {errorState ? (
                <div className="alert alert-danger"></div>
            ) : null}
            {sent && (
                <FlashMessage
                    theme={success ? "success" : ""}
                    text={success ? "Uložení faktury proběhlo úspěšně" : ""}
                />
            )}
            <form onSubmit={handleSubmit}>
                <InputField
                    required={true}
                    type="number"
                    name="invoiceNumber"
                    min="3"
                    label="Číslo faktury"
                    prompt="Zadej číslo"
                    value={invoice.invoiceNumber}
                    handleChange={(e) => {
                        setInvoice({...invoice, invoiceNumber: e.target.value });
                    }}
                />


                <InputSelect
                    required={true}
                    className="browser-default form-select"
                    name="seller"
                    label="Dodavatel"
                    handleChange={(e) => {
                        setInvoice({ ...invoice, seller: { _id: e.target.value} });
                    }}
                    value={invoice.seller.name}
                    items={sellerListSatte}
                />

                <InputSelect
                    required={true}
                    className="browser-default form-select"
                    name="buyer"
                    label="Nakupující"
                    handleChange={(e) => {
                        setInvoice({ ...invoice, buyer: { _id:  e.target.value }});
                    }}
                    value={invoice.buyer.name}
                    items={buyerListState}
                />

                <InputField
                    required={true}
                    type="date"
                    name="issued"
                    min="3"
                    label="Datum vystavení faktury"
                    prompt="Zadejte číslo faktury"
                    value={invoice.issued}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, issued: e.target.value });
                    }}
                />

                <InputField
                    required={true}
                    type="date"
                    name="dueDate"
                    min="3"
                    label="Datum splatnosti faktury"
                    prompt="Zadej datum"
                    value={invoice.dueDate}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, dueDate: e.target.value });
                    }}
                />

                <InputField
                    required={true}
                    type="text"
                    name="product"
                    min="3"
                    label="Jméno produktu"
                    prompt="Zadej název produktu"
                    value={invoice.product}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, product: e.target.value });
                    }}
                />

                <InputField
                    required={true}
                    type="number"
                    name="price"
                    min="3"
                    label="Částka"
                    prompt="Zadej částku např. 1230"
                    value={invoice.price}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, price: e.target.value });
                    }}
                />

                <InputField
                    required={true}
                    type="number"
                    name="vat"
                    min="3"
                    label="DPH v %"
                    prompt="Zadej DPH"
                    value={invoice.vat}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, vat: e.target.value });
                    }}
                />

                <InputField
                    required={false}
                    type="textarea"
                    name="note"
                    min="3"
                    label="Zadejte poznámku"
                    prompt="Poznámka..."
                    value={invoice.note}
                    handleChange={(e) => {
                        setInvoice({ ...invoice, note: e.target.value });
                    }}
                />

                <input type="submit" className="btn btn-primary" value={"Uložit"} />
            </form>

        </div>
    );
};

export default InvoiceForm;