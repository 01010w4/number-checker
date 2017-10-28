public class SequenceCheck {
          public boolean findMask(String mask, String number) {
            char c[] = new char[256];
            boolean used[] = new boolean[256];
            int lm = mask.length();
            int ln = number.length();
            for (int i = 0; i + lm <= ln; i++) {
                boolean ok = true;
                for (int j = 0; j < lm; j++) {
                    if (used[mask.charAt(j)]) {
                        if (number.charAt(i + j) != c[mask.charAt(j)]) {
                            ok = false;
                            break;
                        }
                    } else {
                        used[mask.charAt(j)] = true;
                        c[mask.charAt(j)] = number.charAt(i + j);
                    }
                }
                if (ok) return true;
                for (int j = 0; j < mask.length(); j++) {
                    used[mask.charAt(j)] = false;
                }
            }
            return false;
        }


    }


