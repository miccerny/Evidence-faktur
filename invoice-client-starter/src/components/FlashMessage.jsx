
import React, { useEffect, useState } from "react";

export function FlashMessage({ theme, text, duration = 3000, onClose }) {
  const [visible, setVisible] = useState(false);

  useEffect(() =>{
    if(text) {
      setVisible(true)
    }
  }, [text]);

  useEffect(() => {
      if(!visible) return;
    
    const timer = setTimeout(() => {
      setVisible(false)
      if (onClose) onClose();
    }, duration);

    return () => clearTimeout(timer);

  }, [visible, duration, onClose]);

  if (!visible) return null;


  return <div className={"alert alert-" + theme}>{text}</div>;


}

export default FlashMessage;
