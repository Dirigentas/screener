-- purpose: to make table columns order as I want
CREATE TABLE public.magic_formula (
  ticker character varying(255) NOT NULL,
  earnings_yield double precision NULL,
  return_on_capital double precision NULL,
  yield_rank integer NULL,
  roc_rank integer NULL,
  magic_formula_rank integer NULL,
  latest_quarter_date character varying(255) NULL
);
COMMENT ON COLUMN public.magic_formula.earnings_yield IS 'Measures how much earnings a company generates compared to its price %';
COMMENT ON COLUMN public.magic_formula.return_on_capital IS 'Shows how efficiently a company uses its capital %';
ALTER TABLE public.magic_formula
ADD CONSTRAINT magic_formula_pkey PRIMARY KEY (ticker)