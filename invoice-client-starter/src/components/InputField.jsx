import "bootstrap/dist/css/bootstrap.min.css";

export function InputField(props) {
  // podporované typy pro element input
  const INPUTS = ["text", "number", "date"];

  // validace elementu a typu
  const type = props.type.toLowerCase();
  const isTextarea = type === "textarea";
  const required = props.required || false;

  if (!isTextarea && !INPUTS.includes(type)) {
    return null;
  }

  // přiřazení hodnoty minima do atributu příslušného typu
  const minProp = props.min || null;
  const min = ["number", "date"].includes(type) ? minProp : null;
  const minlength = ["text", "textarea"].includes(type) ? minProp : null;

  const customMessage = props.customValidationMessage || "Vyplňte prosím toto pole.";

  const handleInvalid = (e) => {
    e.target.setCustomValidity(customMessage);
  };

  const handleInput = (e) => {
    e.target.setCustomValidity("");
  };

  return (
    <div className="mb-3">
      <div className="form-label">
        <label className="form-label fw-bold">{props.label}:</label>

        {/* vykreslení aktuálního elementu */}
        {isTextarea ? (
          <textarea
            required={required}
            className="form-control rounded-4 shadow-sm mt-2"
            placeholder={props.prompt}
            rows={props.rows}
            minLength={minlength}
            name={props.name}
            value={props.value}
            onChange={props.handleChange}
            onInvalid={handleInvalid}
            onInput={handleInput}
          />

        ) : (
          <input
            required={required}
            type={type}
            className="form-control rounded-pill shadow-sm mt-2"
            placeholder={props.prompt}
            minLength={minlength}
            min={min}
            name={props.name}
            value={props.value}
            onChange={props.handleChange}
            disabled={props.disabled}
            onInvalid={handleInvalid}
            onInput={handleInput}
          />
        )}
      </div>
    </div>
  );
}

export default InputField;
